package com.eazybytes.jobportal.contact.service;

import com.eazybytes.jobportal.dto.ContactRequestDto;

public interface IContactService {

    public boolean saveContact(ContactRequestDto contactRequestDto);
}
