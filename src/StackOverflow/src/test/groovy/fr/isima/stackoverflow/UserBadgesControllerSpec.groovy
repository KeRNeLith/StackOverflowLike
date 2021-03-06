package fr.isima.stackoverflow

import grails.test.mixin.*
import spock.lang.*

@TestFor(UserBadgesController)
@Mock(UserBadges)
class UserBadgesControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'

        def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
        def badge = new Badge(name: 'Validity', rank: Badge.Rank.GOLD)
        def nowDate = new Date()

        params["user"] = user
        params["badge"] = badge
        params["dateEarned"] = nowDate
        //def usrBad = new UserBadges(user: user, badge: badge, dateEarned: nowDate)

        //assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.userBadgesList
            model.userBadgesCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.userBadges!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def userBadges = new UserBadges()
            userBadges.validate()
            controller.save(userBadges)

        then:"The create view is rendered again with the correct model"
            model.userBadges!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            userBadges = new UserBadges(params)

            controller.save(userBadges)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/userBadges/show/1'
            controller.flash.message != null
            UserBadges.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def userBadges = new UserBadges(params)
            controller.show(userBadges)

        then:"A model is populated containing the domain instance"
            model.userBadges == userBadges
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def userBadges = new UserBadges(params)
            controller.edit(userBadges)

        then:"A model is populated containing the domain instance"
            model.userBadges == userBadges
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/userBadges/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def userBadges = new UserBadges()
            userBadges.validate()
            controller.update(userBadges)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.userBadges == userBadges

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            userBadges = new UserBadges(params).save(flush: true)
            controller.update(userBadges)

        then:"A redirect is issued to the show action"
            userBadges != null
            response.redirectedUrl == "/userBadges/show/$userBadges.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/userBadges/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def userBadges = new UserBadges(params).save(flush: true)

        then:"It exists"
            UserBadges.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(userBadges)

        then:"The instance is deleted"
            UserBadges.count() == 0
            response.redirectedUrl == '/userBadges/index'
            flash.message != null
    }
}
