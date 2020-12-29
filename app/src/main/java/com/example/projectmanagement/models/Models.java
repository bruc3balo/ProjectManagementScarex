package com.example.projectmanagement.models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class Models {

    public static final String LANDLORD_ROLE = "Landlord";
    public static final String TENANT_ROLE = "Tenant";
    public static final String USER_DB = "Users";
    public static final String TENANT_DB = "Tenants";
    public static final String ROLE = "role";
    public static final String CREATED_AT = "createdAt";
    public static final String TENANT_TYPE = "tenantType";
    public static final String TENANT_TYPE_PERSON = "tenantPerson";
    public static final String TENANT_TYPE_COMPANY = "tenantCompany";

    public static class User_Landlord {
        private String IdNumber;
        private String name;
        private String email;
        private String country;
        private String gender;
        private String phoneNumber;
        private String yearOfBirth;
        private String monthOfBirth;
        //private String website;
        private String addressLine;
        private String dateOfBirth;
        private String uid;
        private String role;
        private String createdAt;

        public static final String LANDLORD = "Landlord";


        public User_Landlord() {
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getMonthOfBirth() {
            return monthOfBirth;
        }

        public void setMonthOfBirth(String monthOfBirth) {
            this.monthOfBirth = monthOfBirth;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getIdNumber() {
            return IdNumber;
        }

        public void setIdNumber(String idNumber) {
            IdNumber = idNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }


        public String getYearOfBirth() {
            return yearOfBirth;
        }

        public void setYearOfBirth(String yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }

        public String getAddressLine() {
            return addressLine;
        }

        public void setAddressLine(String addressLine) {
            this.addressLine = addressLine;
        }
    }

    public static class Tenant_Company {
        private String createdAt;
        private String companyName;
        private String phoneNumber;

        private String emailAddress;
        private String companyAddress;
        private String notes;

        private String secret_key;
        private String uid;
        private String role;

        private String tenantType;
        private String landlordUid;


        public Tenant_Company() {
        }

        public String getLandlordUid() {
            return landlordUid;
        }

        public void setLandlordUid(String landlordUid) {
            this.landlordUid = landlordUid;
        }

        public String getTenantType() {
            return tenantType;
        }

        public void setTenantType(String tenantType) {
            this.tenantType = tenantType;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getSecret_key() {
            return secret_key;
        }

        public void setSecret_key(String secret_key) {
            this.secret_key = secret_key;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    public static class Tenant_Person {
        private String uid;
        private String role;
        private String createdAt;
        private String first_name;
        private String last_name;
        private String emailAddress;
        private String phoneNumber;
        private String dob;
        private String notes;
        private String secret_key;
        private String tenantType;
        private String landlordUid;

        public Tenant_Person() {
        }

        public String getLandlordUid() {
            return landlordUid;
        }

        public void setLandlordUid(String landlordUid) {
            this.landlordUid = landlordUid;
        }

        public String getTenantType() {
            return tenantType;
        }

        public void setTenantType(String tenantType) {
            this.tenantType = tenantType;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getSecret_key() {
            return secret_key;
        }

        public void setSecret_key(String secret_key) {
            this.secret_key = secret_key;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class PropertyInfo {

        public static final String PROPERTY_DB = "Properties";
        public static final String PROPERTY_SUR = "PTY";
        private String name;
        public static final String PROPERTY_NAME = "";
        private String propertyId;
        public static final String PROPERTY_ID = "propertyId";
        private String uid;
        private String address1;
        public static final String ADDRESS1 = "address1";
        private String address2;
        public static final String ADDRESS2 = "address2";
        private String suburb;
        public static final String SUBURB = "suburb";
        private String city;
        public static final String CITY = "city";
        private String zipCode;
        public static final String ZIPCODE = "zipCode";
        private String state;
        public static final String STATE = "state";
        private boolean isMultiUnit;
        public static final String IS_MUTLTI_UNIT = "isMultiUnit";
        private String phoneNumber;
        public static final String PHONE_NUMBER = "phoneNumber";
        private int bedrooms;
        public static final String BEDROOMS = "bedrooms";
        private int bathrooms;
        public static final String BATHROOMS = "bathrooms";
        private Double area;
        public static final String AREA = "area";
        private String notes;
        public static final String NOTES = "notes";
        private String imageUri;
        public static final String IMAGE_URL = "imageUri";
        private LatLng propertyPosition;
        public static final String PROPERTY_POSITION = "propertyPosition";
        private String propertyLocation;
        public static final String PROPERTY_LOCATION = "propertyLocation";
        private float rating;
        public static final String RATING = "rating";
        private String attributions;
        public static final String ATTRIBUTIONS = "attributions";
        private String propertyImageUrl;
        public static final String PROPERTY_IMAGE_URL = "propertyImageUrl";
        private String lastModifiedAt;
        public static final String LAST_MODIFIED = "lastModifiedAt";

        public static final String NO_IMAGE_POSTER = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FFile%3ANo_image_available.svg&psig=AOvVaw0Q68_I72mB4OQsVZ3hVTO_&ust=1611869301195000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCNCX7JmHve4CFQAAAAAdAAAAABAD";

        public PropertyInfo() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public String getPropertyLocation() {
            return propertyLocation;
        }

        public void setPropertyLocation(String propertyLocation) {
            this.propertyLocation = propertyLocation;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getSuburb() {
            return suburb;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isMultiUnit() {
            return isMultiUnit;
        }

        public void setMultiUnit(boolean multiUnit) {
            isMultiUnit = multiUnit;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getBedrooms() {
            return bedrooms;
        }

        public void setBedrooms(int bedrooms) {
            this.bedrooms = bedrooms;
        }

        public int getBathrooms() {
            return bathrooms;
        }

        public void setBathrooms(int bathrooms) {
            this.bathrooms = bathrooms;
        }

        public Double getArea() {
            return area;
        }

        public void setArea(Double area) {
            this.area = area;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getImageUri() {
            return imageUri;
        }

        public void setImageUri(String imageUri) {
            this.imageUri = imageUri;
        }

        public LatLng getPropertyPosition() {
            return propertyPosition;
        }

        public void setPropertyPosition(LatLng propertyPosition) {
            this.propertyPosition = propertyPosition;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public String getAttributions() {
            return attributions;
        }

        public void setAttributions(String attributions) {
            this.attributions = attributions;
        }

        public String getPropertyImageUrl() {
            return propertyImageUrl;
        }

        public void setPropertyImageUrl(String propertyImageUrl) {
            this.propertyImageUrl = propertyImageUrl;
        }

        public String getLastModifiedAt() {
            return lastModifiedAt;
        }

        public void setLastModifiedAt(String lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
        }


    }

    public static class PropertyMortgage {

        public static final String MORTGAGE_DB = "Mortgage";

        private String propertyId;
        private String uid;
        private String bankName;
        private String principalAmount;
        private String interestRate;
        private String loanTerm;
        private String repaymentAmount;
        private String amountRepaid;
        private String startDate;
        private String completionDate;
        private String lastModifiedAt;

        public PropertyMortgage() {
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getPrincipalAmount() {
            return principalAmount;
        }

        public void setPrincipalAmount(String principalAmount) {
            this.principalAmount = principalAmount;
        }

        public String getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(String interestRate) {
            this.interestRate = interestRate;
        }

        public String getLoanTerm() {
            return loanTerm;
        }

        public void setLoanTerm(String loanTerm) {
            this.loanTerm = loanTerm;
        }


        public String getRepaymentAmount() {
            return repaymentAmount;
        }

        public void setRepaymentAmount(String repaymentAmount) {
            this.repaymentAmount = repaymentAmount;
        }

        public String getAmountRepaid() {
            return amountRepaid;
        }

        public void setAmountRepaid(String amountRepaid) {
            this.amountRepaid = amountRepaid;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(String completionDate) {
            this.completionDate = completionDate;
        }

        public String getLastModifiedAt() {
            return lastModifiedAt;
        }

        public void setLastModifiedAt(String lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
        }
    }

    public static class PropertyValuation {
        private String propertyId;
        private String uid;
        private String purchaseDate;
        private String purchasePrice;
        private String purchaseTax;
        private String currentValuation;
        private String gainLoss;

        public static final String VALUATION_DB = "Valuation";

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(String purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getPurchaseTax() {
            return purchaseTax;
        }

        public void setPurchaseTax(String purchaseTax) {
            this.purchaseTax = purchaseTax;
        }

        public String getCurrentValuation() {
            return currentValuation;
        }

        public void setCurrentValuation(String currentValuation) {
            this.currentValuation = currentValuation;
        }

        public String getGainLoss() {
            return gainLoss;
        }

        public void setGainLoss(String gainLoss) {
            this.gainLoss = gainLoss;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

}
