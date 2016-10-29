package model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Message
{
    private final long id;
    private final long dialogId;
    private final long userId;
    private final String text;
    private final boolean enable;
}
