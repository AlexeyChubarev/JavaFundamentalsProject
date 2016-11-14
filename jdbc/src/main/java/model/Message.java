package model;

import lombok.Value;
import lombok.AllArgsConstructor;

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
