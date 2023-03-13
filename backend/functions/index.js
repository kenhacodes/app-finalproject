const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

const db = admin.firestore();



exports.helloWorld = functions.https.onCall((data) => {
    const text = data.text;
    console.log(text);
    //const uid = context.auth.uid;
    return text;
})

// CHECK IF USER IS VALID
exports.checkUserValid = functions.https.onCall((data) =>{
    const email = data.email;
    const username = data.username;
    const password = data.password;

    let status, message;
    
    try {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
          return {status: "failed", "message": "Invalid email address. Please enter a valid email address."};
        } else if (email.length > 40) {
          return {status: "failed", message: "Email address is too long. Please enter an email address that is 16 characters or less."};
        }
    
        const usernameRegex = /^[a-zA-Z0-9_]{3,}$/;
        if (!usernameRegex.test(username)) {
          return {status: "failed", message: "Invalid username. Please enter a username that is at least 3 characters long and only contains letters, numbers, and underscores."};
        }
    
        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{8,16}$/;
        if (!passwordRegex.test(password)) {
          let errorMsg = "Invalid password. Please enter a password that meets the following requirements:";
          if (password.length < 8) {
            errorMsg += " at least 8 characters long";
          }
          if (!/(?=.*\d)/.test(password)) {
            errorMsg += ", contains at least one number";
          }
          if (!/(?=.*[a-z])/.test(password)) {
            errorMsg += ", contains at least one lowercase letter";
          }
          if (!/(?=.*[A-Z])/.test(password)) {
            errorMsg += ", contains at least one uppercase letter";
          }
          if (password.length > 25) {
            errorMsg += ", is 16 characters or less";
          }
          return {status: "failed", message: errorMsg};
        }
    
        const emailQuerySnapshot = db.collection("users").where("email", "==", email).get();
        if (!emailQuerySnapshot.empty) {
          return {status: "failed", message: "Email already in use. Please enter a different email address."};
        }
    
        const usernameQuerySnapshot = db.collection("users").where("username", "==", username).get();
        if (!usernameQuerySnapshot.empty) {
          return {status: "failed", message: "Username already in use. Please enter a different username."};
        }
    
        return {status: "ok", message: "OK"};
      } catch (error) {
        console.log(error);
        return {status: "failed", message: "Failed"};
      }

    
})



