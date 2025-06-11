package com.zg.sanctuary.auth.presentation.personal_information

import com.zg.sanctuary.interests.domain.Interest

sealed interface PersonalInformationActions {
    data class OnPickedImage(val image : ByteArray) : PersonalInformationActions {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as OnPickedImage

            if (!image.contentEquals(other.image)) return false

            return true
        }

        override fun hashCode(): Int {
            return image.contentHashCode()
        }
    }

    data class OnChangeFullName(val fullName : String) : PersonalInformationActions
    data class OnChangeUserName(val userName : String) : PersonalInformationActions
    data class OnChangeDob(val dob : String) : PersonalInformationActions
    data class OnInterestPicked(val interest : Interest) : PersonalInformationActions
    class OnCompleteSignUpTapped() : PersonalInformationActions
    class OnTapBack() : PersonalInformationActions
    class OnErrorDialogDismissed() : PersonalInformationActions
}