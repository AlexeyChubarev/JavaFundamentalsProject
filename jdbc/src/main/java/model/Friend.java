package model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Friend
{
    private final long friendId;
    private final String firstName;
    private final String lastName;
}
