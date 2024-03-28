"https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"


   /*<![CDATA[*/
        var itemName = /*[[${item.name}]]*/ '';
        var auctionEndTimeString = /*[[${item.auctionEndTime}]]*/ '';
        /*]]>*/


    $(document).ready(function() {

               $.ajax({
                   url: '/loggedUserID',
                   type: 'GET',
                   success: function(response) {
                       console.log(response);
                       if (response.userId !== '') {

                           $('input[name="userId"]').val(response.userId);
                           console.log(response.userId);
                       } else {
                           alert('You need to log in or register to place a bid.');
                       }
                   },
                   error: function() {
                       console.error('Error checking logged-in status.');
                   }
               });


               $('#bidForm').submit(function(e) {

                   if ($('input[name="userId"]').val() === '') {
                       e.preventDefault(); // Prevent form submission
                       alert('You need to log in or register to place a bid.');
                   } else {

                       var userId = $('input[name="userId"]').val();
                       var actionUrl = '/updateBid/' + itemName + '/' + userId + '/' + $('input[name="bidAmount"]').val();
                       $('#bidForm').attr('action', actionUrl);
                   }
               });
           });

             $(document).ready(function() {
                       var auctionEndTime = new Date(auctionEndTimeString).getTime();

                       var x = setInterval(function() {
                           var now = new Date().getTime();
                           var distance = auctionEndTime - now;
                           var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                           var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                           var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                           var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                           document.getElementById("auctionEndTimer").innerHTML = "Time Remaining to make a bid: " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";

                           if (distance < 0) {
                               clearInterval(x);
                               document.getElementById("auctionEndTimer").innerHTML = "Auction Ended";
                           }
                       }, 1000);
                   });