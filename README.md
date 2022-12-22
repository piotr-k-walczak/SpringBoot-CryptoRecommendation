# Crypto Recommendation Service ( Spring Boot )

---

Tutorial project written in **[ 2022 ]**<br/>
The project written as a 24h recruitment process task.

---

#### Technologies

- Language: **Java 17**
- Framework: **Spring Boot**
- Build: **Maven**
- Testing: **Junit**, **Mockito**
- Other: **Bucket4j**

---

#### Description

The project offers users crypto recommendations based on normalized range. Data is being loaded from a CSV file. If users request data for an unsupported crypto ticker (one that was not present in the file) **UnsupportedSymbolException** is thrown an users received **NOT_ACCEPTABLE** status code.
