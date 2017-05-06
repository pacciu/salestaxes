[![Build Status](https://travis-ci.org/pacciu/salestaxes.svg?branch=master)](https://travis-ci.org/pacciu/salestaxes)

# salestaxes
Test exercise.

WARNING: at version 1.0.0 it could be run only with activeProfile mock (implemented only in src/test). There is no default implementation for TaxRepository.

PROBLEM: SALES TAXES

Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical 
products that are exempt. Import duty is an additional sales tax applicable on all imported goods
at a rate of 5%, with no exemptions.
When I purchase items I receive a receipt which lists the name of all the items and their price 
(including tax), finishing with the total cost of the items, and the total amounts of sales taxes 
paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains 
(np/100 rounded up to the nearest 0.05) amount of sales tax.

This application builds a receipt with shopping baskets respecting the above constraints.


# salestaxes test file
[Test file](src/test/java/com/lastminute/marcoluly/SalestaxesApplicationTests.java) has 3 happy tests having differents shopping baskets with:
- items with import duty taxes
- items with sales taxes
- items with both duty and sales taxes
- items with no taxes

There is also an happy test having a shopping basket with:
- an item with quantity 2
- all items with no taxes

Furthermore, there is a bad test with an item with negative price and shoulds throw a [Negative Price Exception](src/main/java/com/lastminute/marcoluly/exceptions/NotNegativePriceException.java)
