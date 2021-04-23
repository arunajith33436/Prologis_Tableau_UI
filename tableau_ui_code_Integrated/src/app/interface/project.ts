

    export interface Pagination {
        pageNumber: string;
        pageSize: string;
        totalAvailable: string;
    }

    export interface Owner {
        id: string;
    }

    export interface Project {
        owner: Owner;
        id: string;
        name: string;
        description: string;
        createdAt: Date;
        updatedAt: Date;
        contentPermissions: string;
    }

    export interface Projects {
        project: Project[];
    }

    export interface ProjectRootObject {
        pagination: Pagination;
        projects: Projects;
    }



