package demo

import geb.Page

class PlayerIndexPage extends Page {

    static url = '/player/index'

    static at = { title == 'Players' }

    static content = {
        liElements(required: false) { $('li') }
        inputName { $('input#name') }
        submitButton { $('input', value: 'Save') }
        logoutButton { $('input', value: 'Logout') }
    }

    void logout() {
        logoutButton.click()
    }

    int count() {
        if ( liElements.empty ) {
            return 0
        }
        liElements.size()
    }

    void save(String value) {
        inputName = value
        submitButton.click()
    }
}
