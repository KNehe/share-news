# Share news
- A web application where users register/login and can share an event, comment , only delete their own comments and posts, and change their email or password.

## Major dependencies used

- Web and WebSocket
- Spring security
- JPA
- Thymleaf and thymleaf extras
- JQuery
- MySQL
- Cloudinary

## Follow instructions below to run application

- Clone this repository

- Add the DB username and password to ``` application.properties ``` if your not using default values.

- For reset password feature to work add your google username and password to  ``` application.properties ``` for the values ``` spring.mail.username ``` and ``` spring.mail.password ```. [Enable](https://myaccount.google.com/lesssecureapps) less secure app access in your gmail account . You may also need to [enable the secure access capture](https://accounts.google.com/b/0/displayunlockcaptcha). You don't have to use gmail, you can set any other values for another SMTP server.

- Create an account at [Coudinary](https://cloudinary.com/) platform and pick values on your dashboard for the values(api key, cloud name, and secret) ``` coudinary.api.key=, coudinary.cloud.name= ,coudinary.api.secret=, coudinary.folder= ``` to be added in  ``` application.properties ```. This will enable image upload.

- Run the project


## Features

- Sign In/ Sign up
- Forgot password
- Add event(Image and description)
- Delete own event
- Add comment
- Delete own comment
- Change email
- View all comments per post
