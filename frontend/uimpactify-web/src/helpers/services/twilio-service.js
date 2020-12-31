export const getAccessToken = (name) => {
    const AccessToken = require('twilio').jwt.AccessToken;
    const VideoGrant = AccessToken.VideoGrant;
    
    // Used when generating any kind of Access Token
    const twilioAccountSid = 'AC7359df49486b802674a0e8fcef08cabd';
    const twilioApiKey = 'SK624017fe8741c6b1fc8dcbae3e6da517';
    const twilioApiSecret = 'bLbLdmvdfJJtB4KCx2zalqLzL9XRo69i';
    
    // Create an access token which we will sign and return to the client,
    // containing the grant we just created
    const token = new AccessToken(twilioAccountSid, twilioApiKey, twilioApiSecret);
    token.identity = name;
    
    // Create a Video grant which enables a client to use Video 
    // and limits access to the specified Room (DailyStandup)
    const videoGrant = new VideoGrant({
        room: ''
    });
    
    // Add the grant to the token
    token.addGrant(videoGrant);
    
    // Serialize the token to a JWT string
    return token.toJwt();
}