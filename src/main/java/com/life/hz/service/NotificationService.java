package com.life.hz.service;

import com.life.hz.dto.NotificationDTO;
import com.life.hz.dto.PaginationDTO;
import com.life.hz.enums.NOtificationStatusEnum;
import com.life.hz.enums.NOtificationTypeEnum;
import com.life.hz.exception.CustomizeException;
import com.life.hz.exception.CustomizeExceptionCode;
import com.life.hz.mapper.NotificationMapper;
import com.life.hz.mapper.UserMapper;
import com.life.hz.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userID, Integer page, Integer size) {


        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiveEqualTo(userID);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);

        if( totalCount%size == 0 ){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1 ;
        }
        if(page<1){
            page = 1;
        }
        if(page > totalPage ){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page,totalCount);
        Integer offset = size*(page-1);

//      发布的问题集合  分页  limit
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiveEqualTo(userID);
//      按照gmt_modified(更新时间 排序)
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.
                selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if(notifications.size()==0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NOtificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);
        return  paginationDTO;
    }

    public Long unreadCount(Long userId) {

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiveEqualTo(userId)
                .andStatusEqualTo(NOtificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);

    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification == null){
            throw new CustomizeException(CustomizeExceptionCode.NOTIFICATION_NOT_FIND);
        }
        if(!notification.getReceive().equals(user.getId())){
            throw new CustomizeException(CustomizeExceptionCode.READ_NOTIFICATION_FAIL);
        }

//      更新用户通知信息状态 已读 未读
        notification.setStatus(NOtificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NOtificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }
}
