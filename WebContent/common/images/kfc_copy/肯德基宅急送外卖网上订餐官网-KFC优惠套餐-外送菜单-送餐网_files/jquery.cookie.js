/*
 * jQuery Cookie Plugin v1.3
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2011, Klaus Hartl
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.opensource.org/licenses/GPL-2.0
 */
(function(D,B,F){var C=/\+/g;function G(H){return H}function A(H){return decodeURIComponent(H.replace(C," "))}var E=D.cookie=function(J,L,H){if(L!==F){H=D.extend({},E.defaults,H);if(L===null){H.expires=-1}if(typeof H.expires==="number"){var N=H.expires,Q=H.expires=new Date();Q.setDate(Q.getDate()+N)}L=E.json?JSON.stringify(L):String(L);return(B.cookie=[encodeURIComponent(J),"=",E.raw?L:encodeURIComponent(L),H.expires?"; expires="+H.expires.toUTCString():"",H.path?"; path="+H.path:"",H.domain?"; domain="+H.domain:"",H.secure?"; secure":""].join(""))}var K=E.raw?G:A;var R=B.cookie.split("; ");for(var P=0,I=R.length;P<I;P++){var O=R[P].split("=");if(K(O.shift())===J){var M=K(O.join("="));return E.json?JSON.parse(M):M}}return null};E.defaults={};D.removeCookie=function(H,I){if(D.cookie(H)!==null){D.cookie(H,null,I);return true}return false}})(jQuery,document);