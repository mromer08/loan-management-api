package com.chn.exam.customer.specification;

import org.springframework.data.jpa.domain.Specification;

import com.chn.exam.customer.model.Customer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerSpecs {

    public Specification<Customer> searchTermInAttributes(String searchTerm) {
        return Specification.anyOf(
                firstNameContains(searchTerm),
                lastNameContains(searchTerm),
                identificationNumberEquals(searchTerm),
                emailEquals(searchTerm),
                phoneEquals(searchTerm));
    }

    public Specification<Customer> firstNameContains(String firstName) {
        return (root, query, criteriaBuilder) -> (firstName == null || firstName.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstName")),
                        "%" + firstName.toLowerCase() + "%");
    }

    public Specification<Customer> lastNameContains(String lastName) {
        return (root, query, criteriaBuilder) -> (lastName == null || lastName.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lastName")),
                        "%" + lastName.toLowerCase() + "%");
    }

    public Specification<Customer> identificationNumberEquals(String identificationNumber) {
        return (root, query, criteriaBuilder) -> (identificationNumber == null || identificationNumber.isBlank())
                ? null
                : criteriaBuilder.equal(root.get("identificationNumber"), identificationNumber);
    }

    public Specification<Customer> emailEquals(String email) {
        return (root, query, criteriaBuilder) -> (email == null || email.isBlank())
                ? null
                : criteriaBuilder.equal(root.get("email"), email);
    }

    public Specification<Customer> phoneEquals(String phone) {
        return (root, query, criteriaBuilder) -> (phone == null || phone.isBlank())
                ? null
                : criteriaBuilder.equal(root.get("phone"), phone);
    }

}
