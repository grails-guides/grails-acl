package demo

import geb.Page

class LoginPage extends Page {

    static url = '/login/auth'

    static at = { title == 'Login' }

    static content = {
        usernameInput { $('input#username') }
        passwordInput { $('input#password') }
        submitInput { $('input', type: 'submit') }
    }

    void login(String username, String password) {
        usernameInput = username
        passwordInput = password
        submitInput.click()
    }
}
