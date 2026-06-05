package com.eazybytes.jobportal.contact.controller;

import com.eazybytes.jobportal.contact.service.IContactService;
import com.eazybytes.jobportal.dto.ContactRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts/public")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService contactService;

    @PostMapping(version = "1.0")
    public ResponseEntity<String> saveContactMessage(@RequestBody @Valid ContactRequestDto contactRequestDto) {

        boolean isSaved = contactService.saveContact(contactRequestDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Contact message saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save contact message.");
        }
    }

    @GetMapping
    public ResponseEntity<String> fetchOpenContacts(@Validated @RequestParam @NotBlank(message = "Request param status is mandatory")  String status){
      return ResponseEntity.ok("These are contacts with given status "+status);
    }
}
