package com.zg.sanctuary.auth.presentation.personal_information

import com.zg.sanctuary.interests.domain.Interest

data class PersonalInformationState(
    val pickedImage: ByteArray = byteArrayOf(),
    val fullName: String = "",
    val userName: String = "",
    val dob: String = "",
    val interests: List<Interest> = listOf(),
    val chosenInterests: Set<Interest> = setOf(),
    val isLoading: Boolean = false,
    val error: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as PersonalInformationState

        if (isLoading != other.isLoading) return false
        if (!pickedImage.contentEquals(other.pickedImage)) return false
        if (fullName != other.fullName) return false
        if (userName != other.userName) return false
        if (dob != other.dob) return false
        if (interests != other.interests) return false
        if (chosenInterests != other.chosenInterests) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + pickedImage.contentHashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + dob.hashCode()
        result = 31 * result + interests.hashCode()
        result = 31 * result + chosenInterests.hashCode()
        result = 31 * result + error.hashCode()
        return result
    }
}