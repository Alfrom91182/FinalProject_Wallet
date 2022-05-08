# PROGRAM ORIENTED PROGRAMMING 
# FINAL PROJECT
## WALLET APP
### Alfredo Romero Perez

This project is a wallet app to keep track of your income and expenses. 
## Features
- Must be able to search
- Transactions using their notes or date range. 
- Must save transactions to a file. 
## App elements description/function:
- Text Fields:
Amount: Amount to be introduced.
Note: Comments about the operation.
Keyword: Word to be searched.
Available Budget: 
- Buttons:
Income: Button to select if the operation is a income after to introduce the Amount/Notes.  
Expense: Button to select if the operation is a expense after to introduce the Amount/Notes.  
Available Budget: This button is going to sum all the incomes and rest all the expenses to know the current budget available.
Save/Update Operations: This button is going to save the transactions to a Json file.
Search: This button searchs transactions using the notes from each transaction or a date range.
- Date Picker:
Date: Transaction date.
From: Start of the date range.
To: End of date range.



# Classes
## ProjectController
- ## Methods
- incomeSubmitButtonAction
- expenseSubmitButtonAction
- jsonIni 
- availableSubmitButtonAction 
- searchSubmitButtonAction
- search (This method contains a Lambda expression)
- saveSubmitButtonAction
## ProjectModel (This Class contains the tempJason inner class)
- getDate
- readJson
- pieInfo (Element not covered in class)
## ProjectView
- main
- start