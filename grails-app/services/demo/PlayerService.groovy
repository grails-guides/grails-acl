package demo

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize

@Slf4j
@CompileStatic
class PlayerService {

    @Transactional
    Player save(String name) {
        Player player = new Player(name: name)
        if ( !player.save() ) {
            log.error 'unable to save player with {}', name
        }
        player
    }

    @ReadOnly
    @PostFilter('hasPermission(filterObject, admin) or hasRole("ROLE_MANAGER")')
    List<Player> list() {
        Player.findAll()
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasPermission(#identifier, 'demo.Player', admin)")
    @Transactional
    void delete(Serializable identifier) {
        Player.where { id == identifier }.deleteAll()
    }
}