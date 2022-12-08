# GreenBay
Green Fox Academy project : building a "GreenBay" web REST API with spring boot

This is a project to learn new technologies and deepen the technologies used in GFA training, so there will be a lot of explanatory notes in the code.

So please keep this in mind when looking at this repo.

## Features summary
- As a user I can sign in to the application
  - Providing a username and password
- As a signed in user I can create a sellable item
  - Providing the name, description, photo url and price for the item
- As a signed in user I can list existing sellable items
  - With main information about the sellable item: name, photo url and price
  - Only the not yet sold items
- As a signed in user I can view a specific sellable item
  - With all information about the sellable item: name, description, photo url, price and seller's name
- As a signed in user I can buy a sellable item
  - Only if the buyer user have enough greenBay dollars
  - The item becomes not sellable anymore

### List of using packages, technologies:
- Spring boot REST API project (Spring Web, Spring Web DevTools, Lombok) 
- Maven project
- Spring Security with JWT
- Jpa, Hybernate
- MySQL & H2 database (for tests)

### Planned to use:
- Cloud technology to deploy the application in production, like Heroku, Azure etc..
- Dockerization of the project
