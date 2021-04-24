## Documentation
First, to correctly launch Spring Boot Backend application, <br>
you will have to set values for <code>application.properties</code> file.
Each properties sector has been divided with comments.<br>
As first we see MySQL configuration, set it to corresponding mock-up.
<br>
<code>spring.datasource.url=</code> - your database ip or domain name, forwarded with port and database name<br>
<code>spring.datasource.username</code> - obvious one<br>
<code>spring.datasource.password</code> - obvious one as well

The rest of database configuration can be left. No need to change it

Directory where your products images will be saved. <br>
Directory must be in src directory of Angular project, directly, containing assets with product images directory
<br>
<code>e-commerce.product.images.dir=(..pathToAngularSrcFolder)/assets/products-images/</code>
<br>
<br>
Section commented out with ###-config can be left as well.
<br>
<br>
E-mail section, by default has been set for G-mail SMTP Server.
Here, only fields that has to be provided are username and password.
This configuration is responsible for user account creation email verification.
Oh, and one thing to remember, to let spring application log in to your gmail and sent <br>
verification email you will have to go to your Gmail Account and allow third-party application log in.
<br>You should find it in Gmail Security & Privacy sector.
<br>
<code>spring.mail.username=your_username</code>
<br>
<code>spring.mail.password=your_password</code>

<br>
<br>
Last one and hopefully least one is Stripe configuration.
Payment section has to be filled with your clientId and secretId from <code>https://developer.stripe.com</code>

<code>stripe.public.key</code> - Stripe public key
<br>
<code>stripe.secret.key</code> - Stripe secret key


###That's it you are ready to go!
