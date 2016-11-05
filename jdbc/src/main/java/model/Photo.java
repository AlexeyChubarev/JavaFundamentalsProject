package model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.sql.Blob;


@Value
@AllArgsConstructor
public class Photo
{
    private Blob image;
}
