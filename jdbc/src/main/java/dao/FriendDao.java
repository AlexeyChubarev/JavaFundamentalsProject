package dao;

import model.Friend;

import java.util.Collection;

public interface FriendDao
{
    Collection<Friend> getFriends(long userId);
    Collection<Friend> getIncomingRequests(long userId);
    Collection<Friend> getOutgoingRequests(long userId);
}
