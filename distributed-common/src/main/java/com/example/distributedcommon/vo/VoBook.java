package com.example.distributedcommon.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/12/21 17:59
 * description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoBook implements Serializable {

    private String bookName;

}
