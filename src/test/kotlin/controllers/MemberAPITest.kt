package controllers

import models.Member
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertEquals

class MemberAPITest {

    private var learnKotlin: Member? = null
    private var summerHoliday: Member? = null
    private var codeApp: Member? = null
    private var testApp: Member? = null
    private var swim: Member? = null
    private var populatedMembers: MemberAPI? = MemberAPI()
    private var emptyMembers: MemberAPI? = MemberAPI()

    @BeforeEach
    fun setup(){
        learnKotlin = Member("Learning Kotlin", 3, "College", false)
        summerHoliday = Member("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Member("Code App", 4, "Work", true)
        testApp = Member("Test App", 4, "Work", false)
        swim = Member("Swim - Pool", 3, "Hobby", true)

        //adding 5 Member to the Members api
        populatedMembers!!.add(learnKotlin!!)
        populatedMembers!!.add(summerHoliday!!)
        populatedMembers!!.add(codeApp!!)
        populatedMembers!!.add(testApp!!)
        populatedMembers!!.add(swim!!)
    }

    @AfterEach
    fun tearDown(){
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedMembers = null
        emptyMembers = null
    }

    @Nested

    inner class AddMembers {
        @Test
        fun `adding a Member to a populated list adds to ArrayList`() {
            val newMember = Member("Jane", 12030102, "Waterford", false)
            assertEquals(5, populatedMembers!!.numberOfMembers())
            assertTrue(populatedMembers!!.add(newMember))
            assertEquals(6, populatedMembers!!.numberOfMembers())
            assertEquals(newMember, populatedMembers!!.findMember(populatedMembers!!.numberOfMembers() - 1))
        }

        @Test
        fun `adding a Member to an empty list adds to ArrayList`() {
            val newMember = Member("Stacy", 1, "kilkenny", false)
            assertEquals(0, emptyMembers!!.numberOfMembers())
            assertTrue(emptyMembers!!.add(newMember))
            assertEquals(1, emptyMembers!!.numberOfMembers())
            assertEquals(newMember, emptyMembers!!.findMember(emptyMembers!!.numberOfMembers() - 1))
        }
    }

    @Nested
    inner class ListMembers {

        @Test
        fun `listAllMembers returns No Members Stored message when ArrayList is empty`() {
            assertEquals(0, emptyMembers!!.numberOfMembers())
            assertTrue(emptyMembers!!.listMember().lowercase().contains("no members stored"))
        }

        @Test
        fun `listAllMembers returns Members when ArrayList has Members stored`() {
            assertEquals(5, populatedMembers!!.numberOfMembers())
            val MembersString = populatedMembers!!.listMember().lowercase()
            assertTrue(MembersString.contains("learning kotlin"))
            assertTrue(MembersString.contains("code app"))
            assertTrue(MembersString.contains("test app"))
            assertTrue(MembersString.contains("swim"))
            assertTrue(MembersString.contains("summer holiday"))
        }


    }

}