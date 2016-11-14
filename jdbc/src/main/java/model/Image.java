package model;

import lombok.Value;
import lombok.AllArgsConstructor;

import java.sql.Blob;


@Value
@AllArgsConstructor
public class Image
{
    private Blob image;
}
