package net.sufiyan.springboot.service.impl;

import net.sufiyan.springboot.dto.CommentDto;
import net.sufiyan.springboot.entity.Comment;
import net.sufiyan.springboot.entity.Post;
import net.sufiyan.springboot.entity.User;
import net.sufiyan.springboot.mapper.CommentMapper;
import net.sufiyan.springboot.repository.CommentRepository;
import net.sufiyan.springboot.repository.PostRepository;
import net.sufiyan.springboot.repository.UserRepository;
import net.sufiyan.springboot.service.CommentService;
import net.sufiyan.springboot.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {
        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentMapper::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Comment> comments = commentRepository.findCommentsByPost(userId);
        return comments.stream().map((comment)->CommentMapper.mapToCommentDto(comment)).collect(Collectors.toList());
    }
}
