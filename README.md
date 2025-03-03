# Currency Exchange project

This is a test project which get currencies exchange rates and can convert between currencies. 


## Functionality
This project  can do:
<ul>  
<li> Get all exchange rates from Currency A</li>
<li>Get exchange rate from Currency A to Currency B</li>  
<li>Get value conversion from Currency A to Currency B</li>  
<li> Get value conversion from Currency A to a list of supplied currencies</li> 
</ul>



## How to run project

Project can be started in few ways:
1. In CLI run <em>mvn package</em> then <em>java -jar {path}/CurrencyExchange.jar</em>
2. In CLI run  <em>mvn spring-boot:run </em>
3. In any IDE running <em>ExchangeRateChallengeApplication.main

## Testing

Project have UI which contains methods descriptions and allow to test REST API endpoints in convenient way. To access UI you need to use http://localhost:8080/swagger-ui/index.html link. 

## Switch between data provider
Currently there are two data providers - real and dummy. To switch between data provider you need to change <em>real_data_source.enabled </em> property. 

