const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

var db = admin.firestore();





// Returns JSON Object
exports.try = functions.https.onCall((data, context) => {
  
  const user = data.user;
  const email = data.email;
    
  console.log(user);
  console.log(email);

  return {
    user : user,
    email : email
  }

})

// Get Info User (Basic)
exports.getUserInfoBasic = functions.https.onCall(async (data, context) => {

  const uid = data.uid;
  const auth = context.auth;

})




// When Auth delete also del in db


// Update User in db
exports.updateUser = functions.https.onCall(async (data, context) => {
  const uid = data.uid;
  const username = data.uid;
  const email = data.email;
  const about = data.about;
  const icon = data.icon;
  const isArtist = data.isArtist;
  const isChecked = data.isChecked;


})


// Create user in db
exports.insertUserdb = functions.https.onCall(async(data, context) => {
    const uid = data.uid;
    const username = data.username;
    const usernameLowerCase = username.toLowerCase();
    const email = data.email;
    const isArtist = data.isArtist;

    const dataUser = {
      email : email,
      username : username,
      usernamelow : usernameLowerCase,
      isArtist : isArtist
    }

    try{

      
        db.collection('users').doc(uid).get().then(async function(doc) {
          if (doc.exists) {
              
            //Write in database
            await db.collection('users').doc(uid).update({
              email : email
            });
            await db.collection('users').doc(uid).update({
              username : username
            });
            await db.collection('users').doc(uid).update({
              usernamelow : usernameLowerCase
            });
            await db.collection('users').doc(uid).update({
              isArtist : isArtist
            });
            //Return ok if correct
            return {status: "ok", message: uid};
  
          } else {
            const dataUser = {
              username : username,
              email : email,
              usernamelow : username.toLowerCase(),
              isArtist : isArtist,
              about : "Hi!",
              isChecked : false,
              icon : "",
              followers : 0,
              following : 0,
            }
        
            //Write in database
            await db.collection('users').doc(uid).set(dataUser);
            return {status: "ok", message: uid};
          }
        })
        
    }catch(error){
      console.log(error);
      return {status: "failed", message: error};
    }
    }
)

// CHECK IF USER IS VALID
exports.checkUserValid = functions.https.onCall(async (data) =>{
    const email = data.email;
    const username = data.username;
    const password = data.password;

    try {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
          return {
            status: "failed",
            message: "Invalid email address."
          };
        } else if (email.length > 70) {
          return {
            status: "failed",
            message: "Email address is too long."
            };
        }
    
        const usernameRegex = /^[a-zA-Z0-9_]{3,}$/;
        if (!usernameRegex.test(username)) {
          return {
            status: "failed",
            message: "Invalid username."
          };
        }
    
        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{8,16}$/;
        if (!passwordRegex.test(password)) {
          let errorMsg = "Invalid password:";
          if (password.length < 8) {
            errorMsg += " At least 8 characters long";
          }
          if (!/(?=.*\d)/.test(password)) {
            errorMsg += " contains at least one number";
          }
          if (!/(?=.*[a-z])/.test(password)) {
            errorMsg += " contains at least one lowercase letter";
          }
          if (!/(?=.*[A-Z])/.test(password)) {
            errorMsg += " contains at least one uppercase letter";
          }
          if (password.length > 25) {
            errorMsg += " is 16 characters or less";
          }
          return {status: "failed", message: errorMsg};
        }

      const snapshot = await db.collection('users').where('usernamelow', '==', username.toLowerCase()).get();
      if(snapshot.size > 0){
        return {status: "failed", message: "Name already in use."};
      }
    
      return {status: "ok", message: "ok"};

      } catch (error) {
        console.log(error);
        return {status: "failed", message: "Server error. "+ error};
      }

    
})

exports.autoNewUserDB = functions.auth.user().onCreate(async (user) => {

  const dataUser = {
    username : user.email,
    email : user.email,
    usernamelow : user.email.toLowerCase(),
    isArtist : false,
    about : "Hi!",
    isChecked : false,
    icon : "",
    followers : 0,
    following : 0,
  }

  try{
    db.collection('users').doc(uid).get().then(async function(doc) {
      if (doc.exists) {
        console.log("User already exists. Therefore not created. :)")
      } else {
        const dataUser = {
          username : username,
          email : email,
          usernamelow : username.toLowerCase(),
          isArtist : isArtist,
          about : "Hi!",
          isChecked : false,
          icon : "",
          followers : 0,
          following : 0,
        }
        //Write in database
        await db.collection('users').doc(uid).set(dataUser);
      }
    })}catch(error){
        console.log(error)
    }
  })

exports.autoDeleteUserDB = functions.auth.user().onDelete(async (user) => {
  const email = user.email;
  const uid = user.uid;
  const res = await db.collection('users').doc(uid).delete();
  console.log("USER DELETED FROM DATABASE " + email)
});