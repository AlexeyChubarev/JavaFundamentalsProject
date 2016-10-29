package model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Dialog
{
    private final long id;
    private final String name;
}
