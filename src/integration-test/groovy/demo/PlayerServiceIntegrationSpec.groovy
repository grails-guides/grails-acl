package demo

import geb.spock.GebSpec
import grails.testing.mixin.integration.Integration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@Integration
class PlayerServiceIntegrationSpec extends GebSpec {

    RoleService roleService
    UserService userService
    UserRoleService userRoleService
    PlayerService playerService

    def "verify admin users only fetch their own players, managers fetch all"() {
        given:
        Role roleAdmin = roleService.save('ROLE_ADMIN')
        Role roleManager = roleService.save('ROLE_MANAGER')

        User valverde = userService.save('valverde', 'password', true, false, false, false)
        User zidane = userService.save('zidane', 'password', true, false, false, false)
        User tebas = userService.save('tebas', 'password', true, false, false, false)

        userRoleService.save(zidane, roleAdmin)
        userRoleService.save(valverde, roleAdmin)
        userRoleService.save(tebas, roleManager)

        when:
        go PlayerIndexPage.url

        then:
        at LoginPage

        when:
        LoginPage loginPage = browser.page LoginPage
        loginPage.login('valverde', 'password')

        then:
        PlayerIndexPage

        when:
        PlayerIndexPage playerIndexPage = browser.page PlayerIndexPage

        then:
        playerIndexPage.count() == 0

        when:
        playerIndexPage.save('Messi')

        then:
        at PlayerIndexPage
        playerIndexPage.count() == old(playerIndexPage.count()) + 1

        when:
        playerIndexPage.logout()
        go PlayerIndexPage.url

        then:
        at LoginPage

        when:
        loginPage = browser.page LoginPage
        loginPage.login('zidane', 'password')

        then:
        PlayerIndexPage

        when:
        playerIndexPage = browser.page PlayerIndexPage

        then:
        playerIndexPage.count() == 0

        when:
        playerIndexPage.save('Cristiano')

        then:
        at PlayerIndexPage
        playerIndexPage.count() == old(playerIndexPage.count()) + 1

        when:
        playerIndexPage.logout()
        go PlayerIndexPage.url

        then:
        at LoginPage

        when:
        loginPage = browser.page LoginPage
        loginPage.login('tebas', 'password')

        then:
        PlayerIndexPage

        when:
        playerIndexPage = browser.page PlayerIndexPage

        then:
        playerIndexPage.count() == 2

        cleanup:
        login('tebas', 'password')
        List<Player> playerList = playerService.list()
        for ( Player player : playerList ) {
            playerService.delete(player.id)
        }
        logout()

        userRoleService.delete(zidane, roleAdmin)
        userRoleService.delete(valverde, roleAdmin)
        userRoleService.delete(tebas, roleManager)

        roleService.delete(roleAdmin.id)
        roleService.delete(roleManager.id)

        userService.delete(zidane.id)
        userService.delete(valverde.id)
        userService.delete(tebas.id)
    }

    void login(String username, String password) {
        SecurityContextHolder.context.authentication = new UsernamePasswordAuthenticationToken(username, password)
    }

    void logout() {
        SecurityContextHolder.clearContext()
    }
}
