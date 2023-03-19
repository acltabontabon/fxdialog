# FXDialog **(DEPRECATED)**

FXDialog is an experimental component, that aims to provide a nice-looking dialog for JavaFX 2.x.

![](https://i.stack.imgur.com/scXGH.png)


## Features

* **Message Dialog** 
* **Confirm Dialog**
* **Input Dialog**


## **Using FXDialog**

Using FXDialog is really simple. If you're familiar with the JOptionPane of Swing framework you will see FXDialog had similar approach of implementation.



*Note:* Make sure that the [FXDialog v1.3](https://bitbucket.org/tabs5894/fxdialog/downloads/FXDialog%20v1.3.jar) file is place on the classpath.

-----------------------------------------------------------------------------------------------------------



***Using Message Dialog***

  Import statement:
     

```java

import tabs.FXDialog;
import tabs.Message;
```


  Snippet Codes:



```java

FXDialog.showMessageDialog("This is a desmonstration of Information message \ndialog.", "Title Message", Message.INFORMATION);
FXDialog.showMessageDialog("This is a desmonstration of Warning message \ndialog.", "Title Message", Message.WARNING);
FXDialog.showMessageDialog("This is a desmonstration of Error message \ndialog.", "Title Message", Message.ERROR);
```



-----------------------------------------------------------------------------------------------------------




***Using Input Dialog***

  Import statement:


```java

import tabs.FXDialog;
```


  Snippet Codes:



```java

String input = FXDialog.showInputDialog("Enter your favorite number:", "Title Message");

System.out.println(input);
```

-----------------------------------------------------------------------------------------------------------




***Using Confirm Dialog***

  Import statement:


```java

import tabs.ConfirmationType;
import tabs.FXDialog;
```


  Snippet Codes:



```java

boolean confirm = FXDialog.showConfirmDialog("Do you really want to delete the selected item?", "Title Message", ConfirmationType.DELETE_OPTION);
        
if (confirm)
    System.out.println("Accepted!");
else
    System.out.println("Declined!");
```


  Other Confirmation Type:


```java

ConfirmationType.ACCEPT_DECLINE_OPTION
ConfirmationType.YES_NO_OPTION
```





-----------------------------------------------------------------------------------------------------------


Enjoy!
Alvin Cris Tabontabon
