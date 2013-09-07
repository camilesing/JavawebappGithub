
/**
 * MmsImplementationCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */

    package org.tempuri;

    /**
     *  MmsImplementationCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class MmsImplementationCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public MmsImplementationCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public MmsImplementationCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for RemoveTask2 method
            * override this method for handling normal response from RemoveTask2 operation
            */
           public void receiveResultRemoveTask2(
                    org.tempuri.MmsImplementationStub.RemoveTask2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from RemoveTask2 operation
           */
            public void receiveErrorRemoveTask2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for AddTask4 method
            * override this method for handling normal response from AddTask4 operation
            */
           public void receiveResultAddTask4(
                    org.tempuri.MmsImplementationStub.AddTask4Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from AddTask4 operation
           */
            public void receiveErrorAddTask4(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for RemoveTask method
            * override this method for handling normal response from RemoveTask operation
            */
           public void receiveResultRemoveTask(
                    org.tempuri.MmsImplementationStub.RemoveTaskResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from RemoveTask operation
           */
            public void receiveErrorRemoveTask(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for AddTask1 method
            * override this method for handling normal response from AddTask1 operation
            */
           public void receiveResultAddTask1(
                    org.tempuri.MmsImplementationStub.AddTask1Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from AddTask1 operation
           */
            public void receiveErrorAddTask1(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetMessages method
            * override this method for handling normal response from GetMessages operation
            */
           public void receiveResultGetMessages(
                    org.tempuri.MmsImplementationStub.GetMessagesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetMessages operation
           */
            public void receiveErrorGetMessages(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for SendMessage3 method
            * override this method for handling normal response from SendMessage3 operation
            */
           public void receiveResultSendMessage3(
                    org.tempuri.MmsImplementationStub.SendMessage3Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SendMessage3 operation
           */
            public void receiveErrorSendMessage3(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for AddTask3 method
            * override this method for handling normal response from AddTask3 operation
            */
           public void receiveResultAddTask3(
                    org.tempuri.MmsImplementationStub.AddTask3Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from AddTask3 operation
           */
            public void receiveErrorAddTask3(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetMessage method
            * override this method for handling normal response from GetMessage operation
            */
           public void receiveResultGetMessage(
                    org.tempuri.MmsImplementationStub.GetMessageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetMessage operation
           */
            public void receiveErrorGetMessage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for AddTask2 method
            * override this method for handling normal response from AddTask2 operation
            */
           public void receiveResultAddTask2(
                    org.tempuri.MmsImplementationStub.AddTask2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from AddTask2 operation
           */
            public void receiveErrorAddTask2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for SendMessage4 method
            * override this method for handling normal response from SendMessage4 operation
            */
           public void receiveResultSendMessage4(
                    org.tempuri.MmsImplementationStub.SendMessage4Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SendMessage4 operation
           */
            public void receiveErrorSendMessage4(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for SendMessage1 method
            * override this method for handling normal response from SendMessage1 operation
            */
           public void receiveResultSendMessage1(
                    org.tempuri.MmsImplementationStub.SendMessage1Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SendMessage1 operation
           */
            public void receiveErrorSendMessage1(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for SendMessage2 method
            * override this method for handling normal response from SendMessage2 operation
            */
           public void receiveResultSendMessage2(
                    org.tempuri.MmsImplementationStub.SendMessage2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SendMessage2 operation
           */
            public void receiveErrorSendMessage2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetDeliveryReport1 method
            * override this method for handling normal response from GetDeliveryReport1 operation
            */
           public void receiveResultGetDeliveryReport1(
                    org.tempuri.MmsImplementationStub.GetDeliveryReport1Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetDeliveryReport1 operation
           */
            public void receiveErrorGetDeliveryReport1(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetDeliveryReport2 method
            * override this method for handling normal response from GetDeliveryReport2 operation
            */
           public void receiveResultGetDeliveryReport2(
                    org.tempuri.MmsImplementationStub.GetDeliveryReport2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetDeliveryReport2 operation
           */
            public void receiveErrorGetDeliveryReport2(java.lang.Exception e) {
            }
                


    }
    