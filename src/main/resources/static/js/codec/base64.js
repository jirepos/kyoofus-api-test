
const Base64 = { 

    encode(strToEncode) {
      return btoa(unescape(encodeURIComponent(strToEncode)));
    },
    decode(strToDecode) {
      return decodeURIComponent(escape(window.atob(strToDecode)))
    }
  }
  
  