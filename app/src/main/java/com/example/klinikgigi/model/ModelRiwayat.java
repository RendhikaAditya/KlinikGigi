
package com.example.klinikgigi.model;

import com.google.gson.annotations.SerializedName;


public class ModelRiwayat {

    @SerializedName("agama_bumil")
    private String mAgamaBumil;
    @SerializedName("alamat_bumil")
    private String mAlamatBumil;
    @SerializedName("alamat_dokter")
    private String mAlamatDokter;
    @SerializedName("foto_bayar")
    private String mFotoBayar;
    @SerializedName("gender_dokter")
    private String mGenderDokter;
    @SerializedName("id_bayar")
    private String mIdBayar;
    @SerializedName("id_bumil")
    private String mIdBumil;
    @SerializedName("id_dokter")
    private String mIdDokter;
    @SerializedName("id_janji")
    private String mIdJanji;
    @SerializedName("jam")
    private String mJam;
    @SerializedName("nama_bumil")
    private String mNamaBumil;
    @SerializedName("nama_dokter")
    private String mNamaDokter;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("status_bayar")
    private String mStatusBayar;
    @SerializedName("telp_bumil")
    private String mTelpBumil;
    @SerializedName("telp_dokter")
    private String mTelpDokter;
    @SerializedName("tgl_janji")
    private String mTglJanji;
    @SerializedName("tipe_janji")
    private String mTipeJanji;
    @SerializedName("ttl_bumil")
    private String mTtlBumil;
    @SerializedName("ttl_dokter")
    private String mTtlDokter;
    @SerializedName("umur_bumil")
    private String mUmurBumil;
    @SerializedName("username")
    private String mUsername;

    public ModelRiwayat(String mAgamaBumil, String mAlamatBumil, String mAlamatDokter, String mFotoBayar, String mGenderDokter, String mIdBayar, String mIdBumil, String mIdDokter, String mIdJanji, String mJam, String mNamaBumil, String mNamaDokter, String mPassword, String mStatusBayar, String mTelpBumil, String mTelpDokter, String mTglJanji, String mTipeJanji, String mTtlBumil, String mTtlDokter, String mUmurBumil, String mUsername) {
        this.mAgamaBumil = mAgamaBumil;
        this.mAlamatBumil = mAlamatBumil;
        this.mAlamatDokter = mAlamatDokter;
        this.mFotoBayar = mFotoBayar;
        this.mGenderDokter = mGenderDokter;
        this.mIdBayar = mIdBayar;
        this.mIdBumil = mIdBumil;
        this.mIdDokter = mIdDokter;
        this.mIdJanji = mIdJanji;
        this.mJam = mJam;
        this.mNamaBumil = mNamaBumil;
        this.mNamaDokter = mNamaDokter;
        this.mPassword = mPassword;
        this.mStatusBayar = mStatusBayar;
        this.mTelpBumil = mTelpBumil;
        this.mTelpDokter = mTelpDokter;
        this.mTglJanji = mTglJanji;
        this.mTipeJanji = mTipeJanji;
        this.mTtlBumil = mTtlBumil;
        this.mTtlDokter = mTtlDokter;
        this.mUmurBumil = mUmurBumil;
        this.mUsername = mUsername;
    }

    public String getAgamaBumil() {
        return mAgamaBumil;
    }

    public void setAgamaBumil(String agamaBumil) {
        mAgamaBumil = agamaBumil;
    }

    public String getAlamatBumil() {
        return mAlamatBumil;
    }

    public void setAlamatBumil(String alamatBumil) {
        mAlamatBumil = alamatBumil;
    }

    public String getAlamatDokter() {
        return mAlamatDokter;
    }

    public void setAlamatDokter(String alamatDokter) {
        mAlamatDokter = alamatDokter;
    }

    public String getFotoBayar() {
        return mFotoBayar;
    }

    public void setFotoBayar(String fotoBayar) {
        mFotoBayar = fotoBayar;
    }

    public String getGenderDokter() {
        return mGenderDokter;
    }

    public void setGenderDokter(String genderDokter) {
        mGenderDokter = genderDokter;
    }

    public String getIdBayar() {
        return mIdBayar;
    }

    public void setIdBayar(String idBayar) {
        mIdBayar = idBayar;
    }

    public String getIdBumil() {
        return mIdBumil;
    }

    public void setIdBumil(String idBumil) {
        mIdBumil = idBumil;
    }

    public String getIdDokter() {
        return mIdDokter;
    }

    public void setIdDokter(String idDokter) {
        mIdDokter = idDokter;
    }

    public String getIdJanji() {
        return mIdJanji;
    }

    public void setIdJanji(String idJanji) {
        mIdJanji = idJanji;
    }

    public String getJam() {
        return mJam;
    }

    public void setJam(String jam) {
        mJam = jam;
    }

    public String getNamaBumil() {
        return mNamaBumil;
    }

    public void setNamaBumil(String namaBumil) {
        mNamaBumil = namaBumil;
    }

    public String getNamaDokter() {
        return mNamaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        mNamaDokter = namaDokter;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getStatusBayar() {
        return mStatusBayar;
    }

    public void setStatusBayar(String statusBayar) {
        mStatusBayar = statusBayar;
    }

    public String getTelpBumil() {
        return mTelpBumil;
    }

    public void setTelpBumil(String telpBumil) {
        mTelpBumil = telpBumil;
    }

    public String getTelpDokter() {
        return mTelpDokter;
    }

    public void setTelpDokter(String telpDokter) {
        mTelpDokter = telpDokter;
    }

    public String getTglJanji() {
        return mTglJanji;
    }

    public void setTglJanji(String tglJanji) {
        mTglJanji = tglJanji;
    }

    public String getTipeJanji() {
        return mTipeJanji;
    }

    public void setTipeJanji(String tipeJanji) {
        mTipeJanji = tipeJanji;
    }

    public String getTtlBumil() {
        return mTtlBumil;
    }

    public void setTtlBumil(String ttlBumil) {
        mTtlBumil = ttlBumil;
    }

    public String getTtlDokter() {
        return mTtlDokter;
    }

    public void setTtlDokter(String ttlDokter) {
        mTtlDokter = ttlDokter;
    }

    public String getUmurBumil() {
        return mUmurBumil;
    }

    public void setUmurBumil(String umurBumil) {
        mUmurBumil = umurBumil;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
