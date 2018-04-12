package demo

import grails.gorm.services.Service

@Service(UserRole)
interface UserRoleService {
    UserRole save(User user, Role role)
    void delete(User user, Role role)
}