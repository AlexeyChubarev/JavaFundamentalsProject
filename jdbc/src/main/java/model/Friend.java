package model;

import lombok.Value;
import lombok.AllArgsConstructor;

@Value
@AllArgsConstructor
public class Friend
{
    private final long friendId;
    private final String firstName;
    private final String lastName;
}