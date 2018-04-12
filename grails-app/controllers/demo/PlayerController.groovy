package demo

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclUtilService
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.springframework.security.acls.domain.BasePermission

@CompileStatic
class PlayerController {

    PlayerService playerService
    AclUtilService aclUtilService
    SpringSecurityService springSecurityService

    @Secured('isAuthenticated()')
    def index() {
        List<Player> playerList = playerService.list()
        [playerList: playerList]
    }

    @Secured('isAuthenticated()')
    def save(String name) {
        Player player = playerService.save(name)
        if ( !player.hasErrors() ) {
            final String username = loggedUsername()
            aclUtilService.addPermission Player, player.id, username, BasePermission.ADMINISTRATION
        }
        redirect(action: 'index')
    }



    @CompileDynamic
    private String loggedUsername() {
        if ( springSecurityService.principal instanceof String ) {
            return springSecurityService.principal
        }
        if ( springSecurityService.principal instanceof GrailsUser ) {
            return ((GrailsUser) springSecurityService.principal).username
        }
        null
    }
}