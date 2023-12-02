package controllers

import models.Member
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertEquals

class MemberAPITest {

    private var southamerica: Member? = null
    private var spainbeach: Member? = null
    private var earthquate: Member? = null
    private var testing: Member? = null
    private var school: Member? = null
    private var populatedMembers: MemberAPI? = MemberAPI()
    private var emptyMembers: MemberAPI? = MemberAPI()

    @BeforeEach
    fun setup() {
        southamerica = Member(0, "Jane", 830576662, "Waterford", true)
        spainbeach = Member(1, "Akane", 83524343, "Kilkenny", false)
        earthquate = Member(2, "Haley", 334534544, "Waterford", true)
        testing = Member(3, "Tom", 90999954, "Dublin", false)
        school = Member(4, "samsung", 34543533, "Galway", false)

        //adding 5 Member to the Members api
        populatedMembers!!.add(southamerica!!)
        populatedMembers!!.add(spainbeach!!)
        populatedMembers!!.add(earthquate!!)
        populatedMembers!!.add(testing!!)
        populatedMembers!!.add(school!!)
    }

    @AfterEach
    fun tearDown() {
        southamerica = null
        spainbeach = null
        earthquate = null
        testing = null
        school = null
        populatedMembers = null
        emptyMembers = null
    }
    //////////////////////////////
    ///////CRUD JUNIT TESTING/////
    //////////////////////////////
    @Nested
    inner class AddMembers {
        @Test
        fun `adding a Member to a populated list adds to ArrayList`() {
            val newMember = Member(5, "Jane", 12030102, "Waterford", false)
            assertEquals(5, populatedMembers!!.numberOfMembers())
            assertTrue(populatedMembers!!.add(newMember))
            assertEquals(6, populatedMembers!!.numberOfMembers())
            assertEquals(newMember, populatedMembers!!.findMember(populatedMembers!!.numberOfMembers() - 1))
        }

        @Test
        fun `adding a Member to an empty list adds to ArrayList`() {
            val newMember = Member(3, "Stacy", 112452, "kilkenny", false)
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
        fun `listVIPs returns No Members Stored message when ArrayList is empty`() {
            assertEquals(0, emptyMembers!!.numberOfMembers())
            assertTrue(emptyMembers!!.listVIPs().lowercase().contains("no members stored"))
        }
        @Test
        fun `listMembers returns No Members Stored message when ArrayList is empty`(){
            assertEquals(0, emptyMembers!!.numberOfMembers())
            assertTrue(emptyMembers!!.listNormalMembers().lowercase().contains("no members stored"))
        }

        @Test
        fun `listAllMembers returns Members when ArrayList has Members stored`() {
            assertEquals(5, populatedMembers!!.numberOfMembers())
            val membersString = populatedMembers!!.listMember()
            assertTrue(membersString.contains("Jane"))
            assertTrue(membersString.contains("kane"))
            assertTrue(membersString.contains("Haley"))
            assertTrue(membersString.contains("Tom"))
            assertTrue(membersString.contains("samsung"))
        }
        @Test
        fun `listVIPs returns Members when ArrayList has Members stored`() {
            assertEquals(2, populatedMembers!!.numberOfVIPs())
            val vipMembersString = populatedMembers!!.listVIPs()
            assertTrue(vipMembersString.contains("Jane"))
            assertFalse(vipMembersString.contains("Akane"))
            assertTrue(vipMembersString.contains("Haley"))
            assertFalse(vipMembersString.contains("Tom"))
            assertFalse(vipMembersString.contains("sasung"))
        }
        @Test
        fun `listNormalMembers returns Members when ArrayList has Members stored`() {
            assertEquals(3, populatedMembers!!.numberOfNormalMembers())
            val normalMembersString = populatedMembers!!.listNormalMembers()
            assertFalse(normalMembersString.contains("Jane"))
            assertTrue(normalMembersString.contains("Akane"))
            assertFalse(normalMembersString.contains("Haley"))
            assertTrue(normalMembersString.contains("Tom"))
            assertTrue(normalMembersString.contains("samsung"))
        }
    }

    @Nested
    inner class UpdateMembers {
        @Test
        fun `updating a member that does not exist returns false`() {
            assertFalse(populatedMembers!!.updateMember(6, Member(4, "Utter", 2232342, "Waterford", false)))
            assertFalse(populatedMembers!!.updateMember(-3, Member(2, "Nani", 2324234, "South", false)))
            assertFalse(emptyMembers!!.updateMember(0, Member(2, "Jamie", 2423423, "North", false)))
        }
        @Test
        fun `updating a member that exists returns true and updates`(){
            assertEquals(school,populatedMembers!!.findMember(4))
            assertEquals("Samsung",populatedMembers!!.findMember(4)!!.memberName)
            assertEquals(34543533,populatedMembers!!.findMember(4)!!.memberContact)
            assertEquals("Galway",populatedMembers!!.findMember(4)!!.memberAddress)

            assertTrue(populatedMembers!!.updateMember(4,Member(4,"Samsam",103030,"Wexford")))
            assertEquals("Samsam", populatedMembers!!.findMember(4)!!.memberName)
            assertEquals(103030,populatedMembers!!.findMember(4)!!.memberContact)
            assertEquals("Wexford",populatedMembers!!.findMember(4)!!.memberAddress)
        }
        @Test
        fun `upgrading membership for normalmember`(){
            assertEquals(testing,populatedMembers!!.findMember(3))
            assertEquals(false,populatedMembers!!.findMember(3)!!.isMemberVIP)
            assertTrue(populatedMembers!!.upgradeMembership(3))
            assertEquals(true,populatedMembers!!.findMember(3)!!.isMemberVIP)
        }
    }
    @Nested
    inner class DeleteMembers {
        @Test
        fun `deleting member that does not exist, returns null`() {
            assertNull(emptyMembers!!.deleteMember(1))
            assertNull(populatedMembers!!.deleteMember(-2))
            assertNull(populatedMembers!!.deleteMember(6))
        }
        @Test
        fun `deleting member that exists delete`(){
            assertEquals(5, populatedMembers!!.numberOfMembers())
            assertTrue(populatedMembers!!.deleteMember(1))
            assertEquals(4, populatedMembers!!.numberOfMembers())
            assertTrue(populatedMembers!!.deleteMember(2))
            assertEquals(3, populatedMembers!!.numberOfMembers())

        }

    }
    ///////////////////
    //counting method//
    ///////////////////
    @Nested
    inner class CountingMethods {
        @Test
        fun numberOfMembersCalculatedCorrectly() {
            assertEquals(5,populatedMembers!!.numberOfMembers())
            assertEquals(0,emptyMembers!!.numberOfMembers())
        }
        @Test
        fun numberOfVIPsCalculatedCorrectly() {
            assertEquals(2,populatedMembers!!.numberOfVIPs())
            assertEquals(0,emptyMembers!!.numberOfVIPs())
        }
        @Test
        fun numberOfNormalMembersCalculatedCorrectly() {
            assertEquals(3,populatedMembers!!.numberOfNormalMembers())
            assertEquals(0,emptyMembers!!.numberOfNormalMembers())

        }
    }
    //////////////////
    //search methods//
    //////////////////
    @Nested
    inner class SearchMethods {
        @Test
        fun `search members by name returns no members when no members with that name exist`(){
            assertEquals(5,populatedMembers!!.numberOfMembers())
            val searchResults = populatedMembers!!.searchMembersByName("Nobody")
            assertTrue(searchResults.isEmpty())
            assertEquals(0,emptyMembers!!.numberOfMembers())
            assertTrue(emptyMembers!!.searchMembersByName("").isEmpty())
        }
        @Test
        fun `search members by name returns members when members with that name exist`(){
            assertEquals(5,populatedMembers!!.numberOfMembers())
            var searchResults = populatedMembers!!.searchMembersByName("Jane")
            assertTrue(searchResults.contains("Jane"))
            assertFalse(searchResults.contains("Tom"))
            searchResults = populatedMembers!!.searchMembersByName("ane")
            assertTrue(searchResults.contains("Jane"))
            assertTrue(searchResults.contains("Akane"))
            assertFalse(searchResults.contains("Tom"))

        }
    }
}