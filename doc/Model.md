# Data Model

I started writing code, as I usually do, and then things started shaping up to something.  Then I had a chat with wall-e
on WhatsApp and by explaining what I wanted to do with this project I ended up clarifying it to myself...

I'm sure this will change with time, but these are the main features were:

  - [a very intuitive and easy way to capture expenses](https://github.com/krico/trocado/issues/1)
  - [import expenses from multiple sources](https://github.com/krico/trocado/issues/2) (eg extract from bank account CSV)
  - [merging of captured expenses and imported expenses](https://github.com/krico/trocado/issues/3) (automatically when possible)
  - [insertion of recurring or planned expenses](https://github.com/krico/trocado/issues/4) (rent, leasing, insurance)
  - [shared accounts](https://github.com/krico/trocado/issues/5)
  - [predictive expenses](https://github.com/krico/trocado/issues/6): look back and see that you spend an average of 150 a week on groceries, and tell you this month 
    you will probably spend another 289
  - [balance forecast](https://github.com/krico/trocado/issues/7)


And based on these, and my own tracking done in excel, I decided to try to create a solid data model that could account 
for these features.