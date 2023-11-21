package controllers

import models.Member
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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
            val newMember = Member("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedMembers!!.numberOfMembers())
            assertTrue(populatedMembers!!.add(newMember))
            assertEquals(6, populatedMembers!!.numberOfMembers())
            assertEquals(newMember, populatedMembers!!.findMember(populatedMembers!!.numberOfMembers() - 1))
        }

        @Test
        fun `adding a Member to an empty list adds to ArrayList`() {
            val newMember = Member("Study Lambdas", 1, "College", false)
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
            assertTrue(emptyMembers!!.listAllMembers().lowercase().contains("no Members"))
        }

        @Test
        fun `listAllMembers returns Members when ArrayList has Members stored`() {
            assertEquals(5, populatedMembers!!.numberOfMembers())
            val MembersString = populatedMembers!!.listAllMembers().lowercase()
            assertTrue(MembersString.contains("learning kotlin"))
            assertTrue(MembersString.contains("code app"))
            assertTrue(MembersString.contains("test app"))
            assertTrue(MembersString.contains("swim"))
            assertTrue(MembersString.contains("summer holiday"))
        }

        @Test
        fun `listActiveMembers returns no active Members stored when ArrayList is empty`() {
            assertEquals(0, emptyMembers!!.numberOfActiveMembers())
            assertTrue(
                emptyMembers!!.listActiveMembers().lowercase().contains("no active Members")
            )
        }

        @Test
        fun `listActiveMembers returns active Members when ArrayList has active Members stored`() {
            assertEquals(3, populatedMembers!!.numberOfActiveMembers())
            val activeMembersString = populatedMembers!!.listActiveMembers().lowercase()
            assertTrue(activeMembersString.contains("learning kotlin"))
            assertFalse(activeMembersString.contains("code app"))
            assertTrue(activeMembersString.contains("summer holiday"))
            assertTrue(activeMembersString.contains("test app"))
            assertFalse(activeMembersString.contains("swim"))
        }

        @Test
        fun `listArchivedMembers returns no archived Members when ArrayList is empty`() {
            assertEquals(0, emptyMembers!!.numberOfArchivedMembers())
            assertTrue(
                emptyMembers!!.listArchivedMembers().lowercase().contains("no archived Members")
            )
        }

        @Test
        fun `listArchivedMembers returns archived Members when ArrayList has archived Members stored`() {
            assertEquals(2, populatedMembers!!.numberOfArchivedMembers())
            val archivedMembersString = populatedMembers!!.listArchivedMembers().lowercase()
            assertFalse(archivedMembersString.contains("learning kotlin"))
            assertTrue(archivedMembersString.contains("code app"))
            assertFalse(archivedMembersString.contains("summer holiday"))
            assertFalse(archivedMembersString.contains("test app"))
            assertTrue(archivedMembersString.contains("swim"))
        }

        @Test
        fun `listMembersBySelectedPriority returns No Members when ArrayList is empty`() {
            assertEquals(0, emptyMembers!!.numberOfMembers())
            assertTrue(emptyMembers!!.listMembersBySelectedPriority(1).lowercase().contains("no Members")
            )
        }

        @Test
        fun `listMembersBySelectedPriority returns no Members when no Members of that priority exist`() {
            //Priority 1 (1 Member), 2 (none), 3 (1 Member). 4 (2 Members), 5 (1 Member)
            assertEquals(5, populatedMembers!!.numberOfMembers())
            val priority2String = populatedMembers!!.listMembersBySelectedPriority(2).lowercase()
            assertTrue(priority2String.contains("no Members"))
            assertTrue(priority2String.contains("2"))
        }

        @Test
        fun `listMembersBySelectedPriority returns all Members that match that priority when Members of that priority exist`() {
            //Priority 1 (1 Member), 2 (none), 3 (1 Member). 4 (2 Members), 5 (1 Member)
            assertEquals(5, populatedMembers!!.numberOfMembers())
            val priority1String = populatedMembers!!.listMembersBySelectedPriority(1).lowercase()
            assertTrue(priority1String.contains("1 Member"))
            assertTrue(priority1String.contains("priority 1"))
            assertTrue(priority1String.contains("summer holiday"))
            assertFalse(priority1String.contains("swim"))
            assertFalse(priority1String.contains("learning kotlin"))
            assertFalse(priority1String.contains("code app"))
            assertFalse(priority1String.contains("test app"))


            val priority4String = populatedMembers!!.listMembersBySelectedPriority(4).lowercase()
            assertTrue(priority4String.contains("2 Member"))
            assertTrue(priority4String.contains("priority 4"))
            assertFalse(priority4String.contains("swim"))
            assertTrue(priority4String.contains("code app"))
            assertTrue(priority4String.contains("test app"))
            assertFalse(priority4String.contains("learning kotlin"))
            assertFalse(priority4String.contains("summer holiday"))
        }

    }

}