package com.rakha.simpleprojecttemplate.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 *   Created By rakha
 *   2020-01-31
 */
@Parcelize
data class ProfileModel(

    @field:SerializedName("provinsi")
    var provinsi: String? = null,

    @field:SerializedName("kabupaten_kota")
    var kabupatenKota: String? = null,

    @field:SerializedName("contact_person")
    var contactPerson: String? = null,

    @field:SerializedName("id_anggota_fuat")
    var idAnggotaFuat: String? = null,

    @field:SerializedName("phonenumber")
    var phonenumber: String? = null,

    @field:SerializedName("photo_filename")
    var photoFilename: String? = null,

    @field:SerializedName("peta_lokasi")
    var petaLokasi: String? = null,

    @field:SerializedName("kelurahan")
    var kelurahan: String? = null,

    @field:SerializedName("community_id")
    var communityId: Int? = null,

    @field:SerializedName("role_id")
    var roleId: Int? = null,

    @field:SerializedName("mosque_id")
    var mosqueId: Int? = null,

    @field:SerializedName("kecamatan")
    var kecamatan: String? = null,

    @field:SerializedName("fullname")
    var fullname: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("username")
    var username: String? = null,

    @field:SerializedName("id_dompet_madani")
    var idDompetMadani: String? = null
): Parcelable {
    companion object {
        val ROLE_SUPERADMIN = 1
        val ROLE_ADMIN = 2
        val ROLE_USER = 3

        val KOMUNITAS_ID_FUAT = 2

    }
}