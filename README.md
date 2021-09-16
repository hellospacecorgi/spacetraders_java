# 👾 SpaceTraders API Java GUI Application 👾

<img src="https://user-images.githubusercontent.com/24476996/133601795-142665c3-f0c1-4d72-a5d3-b0de1370792e.png" width=450> <img src="https://user-images.githubusercontent.com/24476996/133602038-71ea4552-c674-4654-aa80-f0a166533bb3.png" width=450> <img src="https://user-images.githubusercontent.com/24476996/133602237-72d1c24f-0dbc-44e2-9d1d-452588c8cea1.png" width=450> <img src="https://user-images.githubusercontent.com/24476996/133602460-24435360-eb6e-4ff0-b894-6eaa857147cf.png" width=450> <img src="https://user-images.githubusercontent.com/24476996/133604221-9a3c5475-3d1c-467c-a912-7fcbfae3ea40.png" width=450> <img src="https://user-images.githubusercontent.com/24476996/133604270-7003f4f7-2551-468c-ade1-4d4d357be873.png" width=450>

## Login account for offline version
- Offline version can allow saving of credentials but does not guarantee successful login.
- Only allows the account "username" "token" to be logged in (can be selected from list)
- Manual saving required by clicking "save details" button to add to list.
- Sign up will generate default password "token"

## Run application using Gradle via CLI
`gradle run --args=online` for online version doing GET/POST to API 

or `gradle run --args=offline` for offline version with dummy response data

## Credentials, Tokens
- Signup will display generated token in token text field
- Manual saving required by clicking "save details"
- Allow saving details (any username - token string pair) but does not check for valid pair or not
- Saved credentials are only stored within the session (till application window closes)

## 
The following is grouping of where to perform required actions once logged in:

### Check server status
- Displays server status (active or not) on bottom console message panel

### View Info
- `Load Info` to load user loans and ships
- Displays remaining credits in bottom panel
- `Pay off loan` to pay off loan after selecting loanID (`Load Info` required beforehand)

### Loans
- `View Loans` to list available loans and details
- `Take Out Loan` to obtain loan (require viewing loans and selecting type beforehand)

### Purchase Ships
- `View Ships` to list available ships and details
- `Purchase Ship` require viewing ships and selecting type, purchase location beforehand
- `Purchase Ship Fuel` require `Load My Ships` and then selecting ShipID 
 
### Marketplace
- Response and error messages in bottom left panel, instructions in bottom right
- `View Marketplace` require selecting location, response message in bottom left panel.
- `Place Order` require viewing marketplace, `Load My Ships` and selecting ShipID, Good and Quantity

### Sell Trade Goods
- `Place Sell Order` to sell goods to marketplace (Require `Load My Ships` for loading Ship ID)
- `Load Cargos` to see what goods are available for selling on that ship (after selecting Ship ID)
- Select ShipID , Good and Quantity before placing Sell Order
- Response and error messages in bottom left panel, instructions in bottom right

### Flight Plan
- `View Nearby Planets` to list nearby locations and details (require selecting system)
- `View Nearby Planets`, `Load My Ships` required (load ShipID and destination list) before `Create Flight Plan`
- `View Flight Plan` after selecting PlanID
- Response and error messages in bottom left panel, instructions in bottom right


---

<div>Icons made by 
 
 <a href="https://www.flaticon.com/authors/darius-dan" title="Darius Dan">Darius Dan</a> , <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> , <a href="https://www.freepik.com" title="Freepik">Freepik</a> , <a href="https://www.flaticon.com/authors/icongeek26" title="Icongeek26">Icongeek26</a> , <a href="https://www.flaticon.com/authors/nhor-phai" title="Nhor Phai">Nhor Phai</a> , <a href="" title="smalllikeart">smalllikeart</a> 

All creators from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 

