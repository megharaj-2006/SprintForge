package org.SprintForge.modules.workspace.attachment.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.SprintForge.modules.workspace.attachment.dto.response.AttachmentResponse;
import org.SprintForge.modules.workspace.attachment.dto.response.AttachmentSummaryResponse;
import org.SprintForge.modules.workspace.attachment.entity.Attachment;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AttachmentMapper {

    AttachmentResponse toResponse(Attachment entity);

    AttachmentSummaryResponse toSummaryResponse(Attachment entity);

    List<AttachmentResponse> toResponseList(List<Attachment> entities);

    List<AttachmentSummaryResponse> toSummaryResponseList(List<Attachment> entities);
}
