package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.*;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.PostMapper;
import online.reiam.share.mapper.UserFollowMapper;
import online.reiam.share.request.PostRequest;
import online.reiam.share.response.PostResponse;
import online.reiam.share.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 用户贴子表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PostDetailService postDetailService;
    @Resource
    private PostMapper postMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AtMeService atMeService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicPostService topicPostService;
    @Resource
    private UserFollowMapper userFollowMapper;

    /**
     * 艾特正则表达式
     */
    private Pattern pattern = Pattern.compile("@(?<name>[a-zA-Z\\d_\\u4e00-\\u9fa5]{1,14})\\s+");
    /**
     * 话题正则表达式
     */
    private Pattern pattern2 = Pattern.compile("#(?<name>[a-zA-Z\\d_\\u4e00-\\u9fa5]{1,14})\\s+");

    /**
     * 贴子是否存在
     */
    @Override
    public Post exist(Integer postId) {
        Post post = getById(postId);
        if (post == null) {
            throw new MicroShareException(10024, "贴子不存在。");
        }
        return post;
    }

    /**
     * 发布新贴子
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse create(Integer userId, PostRequest postRequest) {
        // 检查图片是否存在以及排序是否重复
        Map<Integer, Integer> integerMap = new HashMap<>();
        for (int i = 0; i < postRequest.getPostDetailRequestList().size(); i++) {
            Integer sort = postRequest.getPostDetailRequestList().get(i).getSort();
            if (!integerMap.containsKey(sort)) {
                integerMap.put(sort, sort);
            } else {
                throw new MicroShareException(10022, "排序值有重复。");
            }
            Integer resourceId = postRequest.getPostDetailRequestList().get(i).getResourceId();
            online.reiam.share.entity.Resource resource = resourceService.getById(resourceId);
            if (resource == null) {
                throw new MicroShareException(10023, "部分资源不存在。");
            }
        }
        // 插入用户贴子表
        Post post = new Post();
        post.setUserId(userId)
                .setTitle(postRequest.getTitle())
                .setLikesNum(0)
                .setCommentNum(0)
                .setAllowComment(postRequest.getAllowComment())
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        save(post);
        // 插入贴子详情表
        List<PostDetail> postDetailList = new ArrayList<>();
        for (int i = 0; i < postRequest.getPostDetailRequestList().size(); i++) {
            PostDetail postDetail = new PostDetail();
            postDetail.setPostId(post.getId())
                    .setSort(postRequest.getPostDetailRequestList().get(i).getSort())
                    .setResourceId(postRequest.getPostDetailRequestList().get(i).getResourceId())
                    .setDeleted(false)
                    .setVersion(0)
                    .setCreateTime(LocalDateTime.now())
                    .setModifiedTime(LocalDateTime.now());
            postDetailList.add(postDetail);
        }
        postDetailService.saveBatch(postDetailList);
        // 如果正文有@某个用户，则插入通知记录
        Matcher matcher = pattern.matcher(postRequest.getTitle());
        while (matcher.find()) {
            String name = matcher.group("name");
            // 判断用户是否存在
            UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, name));
            if (userInfo != null) {
                AtMe atMe = new AtMe();
                atMe.setTypeId(post.getId())
                        .setUserId(userInfo.getUserId())
                        .setAtMeType(0)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                atMeService.save(atMe);
            }
        }
        // 如果正文有#某个话题，则更新话题贴子数量
        Matcher matcher2 = pattern2.matcher(postRequest.getTitle());
        while (matcher2.find()) {
            String name = matcher2.group("name");
            // 判断话题是否存在
            Topic topic = topicService.getOne(new QueryWrapper<Topic>().lambda().eq(Topic::getName, name));
            if (topic == null) {
                // 若话题不存在，则自动创建新的话题
                topic = new Topic();
                topic.setName(name)
                        .setFollowNum(0)
                        .setPostNum(0)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                topicService.save(topic);
            }
            // 插入贴子话题表
            TopicPost topicPost = new TopicPost();
            topicPost.setTopicId(topic.getId())
                    .setPostId(post.getId())
                    .setDeleted(false)
                    .setVersion(0)
                    .setCreateTime(LocalDateTime.now())
                    .setModifiedTime(LocalDateTime.now());
            topicPostService.save(topicPost);
            // 更新话题相关贴子数
            Topic topic2 = new Topic();
            topic2.setId(topic.getId())
                    .setPostNum(topic.getPostNum() + 1)
                    .setVersion(topic.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            topicService.updateById(topic2);
        }
        return postMapper.selectPostById(post.getId());
    }

    /**
     * 删除贴子及贴子详情
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer userId, Integer postId) {
        Post post = getById(postId);
        if (post == null) {
            throw new MicroShareException(10024, "贴子不存在。");
        }
        if (!post.getUserId().equals(userId)) {
            throw new MicroShareException(10025, "不属于你的贴子。");
        }
        removeById(postId);
        postDetailService.remove(new QueryWrapper<PostDetail>().lambda().eq(PostDetail::getPostId, postId));
    }

    /**
     * 获取某个用户的贴子列表
     */
    @Override
    public IPage<PostResponse> listPostByUserId(PostRequest postRequest) {
        UserInfo userInfo = userInfoService.userExist(postRequest.getNickname());
        Page<PostResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postMapper.selectPostListByUserId(page, userInfo.getUserId());
    }

    /**
     * 刷新关注的用户的贴子列表
     */
    @Override
    public IPage<PostResponse> listPostByFollowId(Integer userId, PostRequest postRequest) {
        // 需要获取全部关注列表
        List<Integer> integerList = userFollowMapper.selectFollowIdListByUserId(userId);
        // 关注列表的新贴子需要分页
        Page<PostResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postMapper.selectPostListByFollowId(page, integerList, postRequest.getStartTime(), postRequest.getEndTime());
    }

}
