/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/shemidepangzi/Documents/Android/workspace/Task1PositionLogger/src/de/uni_stuttgart/yi/task1positionlogger/IPositionLoggerService.aidl
 */
package de.uni_stuttgart.yi.task1positionlogger;
public interface IPositionLoggerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService
{
private static final java.lang.String DESCRIPTOR = "de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService interface,
 * generating a proxy if needed.
 */
public static de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService))) {
return ((de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService)iin);
}
return new de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getLocation:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getLocation();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getDistance:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getDistance();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getAverageSpeed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getAverageSpeed();
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements de.uni_stuttgart.yi.task1positionlogger.IPositionLoggerService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String getLocation() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLocation, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getDistance() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDistance, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getAverageSpeed() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAverageSpeed, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getDistance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getAverageSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public java.lang.String getLocation() throws android.os.RemoteException;
public java.lang.String getDistance() throws android.os.RemoteException;
public java.lang.String getAverageSpeed() throws android.os.RemoteException;
}
