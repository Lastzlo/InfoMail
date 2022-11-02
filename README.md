![InfoMail-logo](https://imgur.com/EyQPoMx.png)

# InfoMail

## About the project

> InfoMail is an email service that will help you:
> 1) Email one or more addresses;
> 2) Schedule email sending on date;
> 3) Resend emails at specified intervals;
> 4) Creating and sharing templates

## Specifics

1) **JWT**-token authentication
2) _Two-factor_ authentication
3) Real _emails_ sending
4) _Docker-compose_  with all services

### Project links

* [infomail](https://infomail-frontend.herokuapp.com/) - InfoMail site
* [swagger-ui](https://infomail-backend.herokuapp.com/swagger-ui/index.html?configUrl=/api/v1/api-docs/swagger-config) - API

### Front-end part of the project

* [infomail-frontend](https://github.com/Lastzlo/Infomail-frontend) - GitHub

## Start the project locally

### Required to install

* Java 17
* PostgreSQL (9.5.9 or higher)
* Docker
* Mail sender Gmail SMTP or MailHog

### Installing

1) Clone this repository to your local machine using:

```shell
git clone https://github.com/Lastzlo/InfoMail.git
```

2) Create a database
3) Set-up Gmail SMTP or MailHog
4) Create environmental variables that are defined in `application.yml`

```properties example
INFOMAIL_DEFAULT_TIMEZONE=Europe/Kiev;
INFOMAIL_FRONT_REG_LINK=http://localhost:4200/#/auth/registration/;
INFOMAIL_FRONT_URL=http://localhost:4200;
INFOMAIL_SECURITY_SECRET=onfn923oiNkfJj32fsl230cdl3mcLNL42lfl932O23Fknlfsdlsf32f04f;
MAIL_EMAIL=infomail;
MAIL_HOST=localhost;
MAIL_PASSWORD=infomail;
MAIL_PORT=1025;
SPRING_DATASOURCE_PASSWORD=postgrespw;
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/infomail;
SPRING_DATASOURCE_USERNAME=postgres
```

5) Set up environmental variables in `application.yml` or in Intellij
   Idea (`Edit Configurations... -> Environmental variables:`)

<img src="https://i.imgur.com/RXEIABL.png" alt="Environmental variables in Intellij
Idea" width="500"/>
