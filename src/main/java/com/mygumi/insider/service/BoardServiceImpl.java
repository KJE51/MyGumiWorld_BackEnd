package com.mygumi.insider.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mygumi.insider.mapper.BoardMapper;
import com.mygumi.insider.dto.BoardDto;
import com.mygumi.insider.dto.CommentDto;
import com.mygumi.insider.dto.ReplyCommentDto;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<BoardDto> getBoards() throws Exception{
        return boardMapper.getBoards();
    }

    @Override
    public BoardDto getBoardDetail(int boardNo) throws Exception {
        boardMapper.updateHit(boardNo);
        BoardDto boardDto = boardMapper.getBoardDetail(boardNo);
        boardDto.setLikesNum(boardMapper.getLikesNum(boardNo));
        return boardDto;
    }

    @Override
    public List<CommentDto> getBoardcomments(int boardNo) throws Exception {
        List<CommentDto> comments = boardMapper.getComments(boardNo);
        List<ReplyCommentDto> replys = boardMapper.getReplys(boardNo);
        int idx = 0, size = replys.size();
        String replyIdx;
        // 대댓글을 댓글 안에 하나씩 넣기
        for(int i = 0; i < size; i++) {
            replyIdx = replys.get(i).getCommentNo();
            while(true) {
                if(comments.get(idx).getCommentNo().equals(replyIdx)) {
                    comments.get(idx).addReply(replys.get(i));
                    break;
                }
                else
                    idx++;
            }
        }
        return comments;
    }

    @Override
    @Transactional
    public void writeBoard(BoardDto boardDto) throws Exception {
        if(boardDto.getTitle() == null || boardDto.getContent() == null)
            throw new Exception();
        boardMapper.writeBoard(boardDto);
    }

    @Override
    public void writeComment(CommentDto commentDto) throws Exception {
        boardMapper.writeComment(commentDto);
    }

    @Override
    public void writeReply(ReplyCommentDto replyDto) throws Exception {
        boardMapper.writeReply(replyDto);
    }

    @Override
    public void modifyBoard(BoardDto boardDto) throws Exception {
        boardMapper.modifyBoard(boardDto);
    }

    @Override
    public void modifyComment(CommentDto commentDto) throws Exception {
        boardMapper.modifyComment(commentDto);
    }

    @Override
    public void modifyReply(ReplyCommentDto replyDto) throws Exception {
        boardMapper.modifyReply(replyDto);
    }

    @Override
    public void deleteBoard(String boardNo) throws Exception {
        boardMapper.deleteBoard(boardNo);
    }

    @Override
    public void deleteComment(String commentNo) throws Exception {
        boardMapper.deleteComment(commentNo);
    }

    @Override
    public void deleteReply(String replyNo) throws Exception {
        boardMapper.deleteReply(replyNo);
    }

    @Override
    public void likeBoard(String boardNo, long id) throws Exception {
        boardMapper.likeBoard(boardNo, id);
    }
}

