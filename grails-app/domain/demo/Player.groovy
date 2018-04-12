package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Player {

    String name

    static constraints = {
        name nullable: false
    }
}
