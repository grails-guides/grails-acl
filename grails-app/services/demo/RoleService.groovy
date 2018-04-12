package demo

import grails.gorm.services.Service

@Service(Role)
interface RoleService {

    Role save(String authority)

    void delete(Serializable id)
}