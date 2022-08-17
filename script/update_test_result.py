import sys
import requests
import json

"""
This python3 script parses the test output file for the associated Zephyr Test Case IDs and updates them to
'Pass' or 'Fail' if they are in any other state
"""
jira_username = ''
jira_password = ''
jira_headers = {}
filepath = ''

jira_base_url = 'https://toranotec.atlassian.net/'
jira_project_key = 'MS'
search_key = 'TestCaseId'
automated_transition_id = '81'
automated_status_id = '12829'

project_summary = {
    'cycleId': '30ba5a16-8fba-44ca-9aff-a7c408fc48ad',
    'folderIds': {
        'MS-API_Test_cases': '2f5bae57-94dc-4ef4-b4e1-582e86b23c8a',
    },
    'projectId': 10022
}
jira_headers = {
    'Content-Type': 'application/json;charset=UTF-8',
    'Accept': 'application/json',
    'x-acpt': sys.argv[1]
}


def read_in_zephyr_tests():
    json_data = ''
    with open(filepath, encoding='UTF-8') as file:
        json_data = json.load(file)
    print("There are around {} features were run".format(len(json_data)))
    tickets_list = []
    for data in json_data:
        for element in data['elements']:
            d = {}
            tags = element['tags']
            tag_names = [x['name'] for x in element['tags'] if
                         (search_key in x['name'] and jira_project_key in x['name'])]
            for tag_name in tag_names:
                jira_id = tag_name[tag_name.find(jira_project_key + '-'):]
                d['status'] = True
                status_steps = [x['result']['status'] for x in element['steps']]
                status = status_steps
                if 'failed' in status or 'skipped' in status:
                    d['status'] = False
                d['ticket'] = jira_id.strip()
                tickets_list.append(d)
    return tickets_list


def search_test_cases_list(tickets):
    test_cases = []
    execution_url = 'https://prod-play.zephyr4jiracloud.com/connect/public/rest/api/1.0/execution/'
    android_folder_ids = [value for key, value in project_summary['folderIds'].items()]
    print('There are around {} test cycles present for android'.format(len(android_folder_ids)))
    cycle_count = 0
    for value in android_folder_ids:
        url = 'https://prod-play.zephyr4jiracloud.com/connect/public/rest/api/2.0/executions/search/cycle/30ba5a16-8fba-44ca-9aff-a7c408fc48ad?projectId=10022&versionId=-1&offset=0&action=expand&sortBy=&sortOrder=asc&size=10&_=1660622143640'
        response = execute_request(url, 'GET')
        if response.status_code == 200:
            for ticket in tickets:
                for test_case in json.loads(response.text)['searchResult']['searchObjectList']:
                    url = execution_url + test_case['execution']['id'] + '?projectId=10022&issueId=' + str(test_case['execution']['issueId']) + '&_=1660406964115'
                    if ticket['ticket'] == test_case['issueKey']:
                        test_cases.append(update_jira_result_status(url, ticket, test_case))
        else:
            print('Status code :: ', response.status_code)
            print('Message :: ', response.text)
        cycle_count += 1
        print('Searched and updated {count} test cycle and yet to update {remaining} test cycles'.format(count=cycle_count, remaining=len(android_folder_ids)-cycle_count))
    print('Summary - ', test_cases)


def execute_request(url, method, data=None, timeout=10):
    response = None
    attempts = 0
    while attempts < 3:
        try:
            if method.upper() == 'GET':
                response = requests.get(url, headers=jira_headers, timeout=timeout)
            if method.upper() == 'PUT':
                response = requests.put(url, data=data, headers=jira_headers, timeout=timeout)
            break
        except requests.exceptions.ReadTimeout as e:
            attempts += 1
            print('Request timeout error occurred', e)
    return response


def update_jira_result_status(url, ticket, test_case):
    request_body = dict()
    res = dict()
    if ticket['status']:
        request_body = get_pass_status_request_body(test_case)
    else:
        request_body = get_fail_status_request_body(test_case)
    response = execute_request(url, 'PUT', data=request_body)
    if response.status_code == 200:
        response = json.loads(response.text)
        res['ticket'] = response['issueKey']
        res['status'] = response['execution']['status']['name']
    return res


def get_un_executed_status_request_body(test_case):
    data_un_executed = json.dumps(
        {"cycleId": test_case['execution']['cycleId'],
         "id": test_case['execution']['id'], "issueId": test_case['execution']['issueId'],
         "projectId": 10022, "status": {"id": -1}, "versionId": -1})
    return data_un_executed


def get_fail_status_request_body(test_case):
    data_fail = json.dumps(
        {"cycleId": test_case['execution']['cycleId'],
         "id": test_case['execution']['id'], "issueId": test_case['execution']['issueId'],
         "projectId": 10022, "status": {"id": 2}, "versionId": -1})
    return data_fail


def get_pass_status_request_body(test_case):
    print(test_case)
    print()
    data_pass = json.dumps(
        {"cycleId": test_case['execution']['cycleId'],
         "id": test_case['execution']['id'], "issueId": test_case['execution']['issueId'],
         "projectId": 10022, "status": {"id": 1}, "versionId": -1})
    return data_pass


if __name__ == '__main__':
    filepath = '../target/cucumber-reports/cucumber.json'
    print('Fetching result data from file!')
    jira_tickets = read_in_zephyr_tests()
    print('Fetched result data from file!')
    print('Started updating result in jira...!')
    search_test_cases_list(jira_tickets)
